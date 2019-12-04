FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=1e218c30526ea74ed2ddecd8126c982e"

SRCBRANCH = "release-chef"
SRC_URI = "\
    git://coral.googlesource.com/imx-board-wlan;protocol=https;branch=${SRCBRANCH} \
"

SRCREV = "314516ad4d36dff99b75ddddb5dea43a157859bf"

# imx-board-wlan uses a python tool to generate
# wifi & bt MAC addresses at runtime. Can't
# install python just for this, so we re-wrote it in C++
SRC_URI_append = " \
    file://setup_mac.cpp \
    file://btfw_loader.service \
"

inherit systemd

S = "${WORKDIR}/git"

FILES_${PN} += " /lib/firmware/* \
                 /lib/systemd/system/imx-board-wlan.service \
                 /lib/systemd/system/btfw_loader.service \
                 /usr/sbin/setup_mac \
                 /etc/wlan_mac.bin \
                 /etc/bluetooth/bt_nv.bin \
"

do_compile() {
    # This is created by us, since rootfs is readonly and we also cannot
    # afford to install python just for one upstream tool. Without this, it will
    # take 1 minute for the wlan driver to startup wifi, and will always use the same
    # default MAC address for all coral boards. BT also has the MAC provided by this tool.
    ${CXX} ${WORKDIR}/setup_mac.cpp ${LDFLAGS} -o ${WORKDIR}/setup_mac

    # We need to generate the wlan MAC binary before starting the driver
    sed -i 's/Type=oneshot/&\nExecStartPre=\/usr\/sbin\/setup_mac/g' ${S}/lib/systemd/system/imx-board-wlan.service
}

do_install() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/lib/systemd/system/imx-board-wlan.service ${D}${systemd_unitdir}/system/imx-board-wlan.service
    install -m 0644 ${WORKDIR}/btfw_loader.service ${D}${systemd_unitdir}/system/btfw_loader.service

    install -d ${D}/lib/firmware/wlan
    install -m 0644 ${S}/lib/firmware/bdwlan30.bin ${D}/lib/firmware/bdwlan30.bin
    install -m 0644 ${S}/lib/firmware/otp30.bin ${D}/lib/firmware/otp30.bin
    install -m 0644 ${S}/lib/firmware/qwlan30.bin ${D}/lib/firmware/qwlan30.bin
    install -m 0644 ${S}/lib/firmware/utf30.bin ${D}/lib/firmware/utf30.bin
    install -m 0644 ${S}/lib/firmware/wlan/qcom_cfg.ini ${D}/lib/firmware/wlan/qcom_cfg.ini

    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/setup_mac ${D}${sbindir}/setup_mac

    # Create placeholder simlink from wlan fw config dir to /tmp, where setup_mac will place mac binaries
    ln -s /tmp/wlan_mac.bin ${D}/lib/firmware/wlan/wlan_mac.bin

    install -d ${D}/etc/bluetooth
    ln -s /tmp/bt_nv.bin ${D}/etc/bluetooth/.bt_nv.bin
}

SYSTEMD_SERVICE_${PN} = "imx-board-wlan.service btfw_loader.service"
