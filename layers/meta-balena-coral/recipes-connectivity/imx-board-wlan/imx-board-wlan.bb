FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=51d4c6e193b82a7bfeb4ced13ddb10a4"

SRCBRANCH = "release-day"
SRC_URI = "\
    git://coral.googlesource.com/imx-board-wlan;protocol=https;branch=${SRCBRANCH} \
"

SRCREV = "99cb9feb00021d6d2ed48cc0756839c41b737630"

# imx-board-wlan uses a python tool to generate
# wifi & bt MAC addresses at runtime. Can't
# install python just for this, so we re-wrote it in C++
SRC_URI_append = " \
    file://setup_mac.cpp \
    file://btfw_loader.service \
    file://imx-board-wlan.service \
    file://wlan.conf \
"

inherit systemd

S = "${WORKDIR}/git"

# In release-day, imx-board-wlan service file is removed,
# so we add our own to trigger the MAC binary generation
FILES_${PN} += " /lib/firmware/* \
                 /lib/systemd/system/imx-board-wlan.service \
                 /lib/systemd/system/btfw_loader.service \
                 /usr/sbin/setup_mac \
                 /etc/wlan_mac.bin \
                 /etc/bluetooth/bt_nv.bin \
                 /etc/modprobe.d/* \
"

do_compile() {
    # This is created by us, since rootfs is readonly and we also cannot
    # afford to install python just for one upstream tool. Without this, it will
    # take 1 minute for the wlan driver to startup wifi, and will always use the same
    # default MAC address for all coral boards. BT also has the MAC provided by this tool.
    ${CXX} ${WORKDIR}/setup_mac.cpp ${LDFLAGS} -o ${WORKDIR}/setup_mac
}

do_install() {
    install -d ${D}${systemd_unitdir}/system
    # TODO: Create our own service file now, in release-day there's no more imx-board-wlan.service
    install -m 0644 ${WORKDIR}/imx-board-wlan.service ${D}${systemd_unitdir}/system/imx-board-wlan.service
    install -m 0644 ${WORKDIR}/btfw_loader.service ${D}${systemd_unitdir}/system/btfw_loader.service

    install -d ${D}/lib/firmware/wlan
    install -m 0644 ${S}/debian/firmware/bdwlan30.bin ${D}/lib/firmware/bdwlan30.bin
    install -m 0644 ${S}/debian/firmware/otp30.bin ${D}/lib/firmware/otp30.bin
    install -m 0644 ${S}/debian/firmware/qwlan30.bin ${D}/lib/firmware/qwlan30.bin
    install -m 0644 ${S}/debian/firmware/utf30.bin ${D}/lib/firmware/utf30.bin
    install -m 0644 ${S}/debian/firmware/wlan/qcom_cfg.ini ${D}/lib/firmware/wlan/qcom_cfg.ini

    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/setup_mac ${D}${sbindir}/setup_mac

    # Create placeholder simlink from wlan fw config dir to /tmp, where setup_mac will place mac binaries
    ln -s /tmp/wlan_mac.bin ${D}/lib/firmware/wlan/wlan_mac.bin

    install -d ${D}/etc/bluetooth
    ln -s /tmp/bt_nv.bin ${D}/etc/bluetooth/.bt_nv.bin

    # MODULE_PROBECONF doesn't seem to work, so we create the blacklist ourselves
    # so we can setup the MAC before loading the driver.
    install -d ${D}/etc/modprobe.d
    install -m 0644 ${WORKDIR}/wlan.conf ${D}/etc/modprobe.d/wlan.conf
}

SYSTEMD_SERVICE_${PN} = " imx-board-wlan.service btfw_loader.service"
