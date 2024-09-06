# License file has been removed in release-day
LICENSE = "CLOSED"

# Bluetooth firmwares were mixed up with the u-boot ones
# for some reason. This is why we need use imx-firmware repo.
SRCBRANCH = "release-day"
SRC_URI = "\
    git://coral.googlesource.com/imx-firmware;protocol=https;branch=${SRCBRANCH} \
"
SRCBRANCH:asus-tinker-edge-t = "mendel-day-imx_8m"
SRC_URI:asus-tinker-edge-t = "\
    git://github.com/TinkerEdgeT/mendel-imx-firmware;protocol=https;branch=${SRCBRANCH:asus-tinker-edge-t} \
"

# Using HEAD revision starting from now, since we can bring in BT
# firmware at the right SHA from linux-firmware
SRCREV = "496dec21f0d2a086a423c92e87eb14dd5ad921b3"

inherit allarch

S = "${WORKDIR}/git"

FILES:${PN} += " /lib/firmware/* "

do_install() {
    install -d ${D}/lib/firmware/
    install -m 0644 ${S}/nvm_tlv_3.2.bin ${D}/lib/firmware/nvm_tlv_3.2.bin

    # This is added in release-day branch
    install -d ${D}/lib/firmware/imx/sdma
    install -m 0644 ${S}/imx/sdma/sdma-imx7d.bin ${D}/lib/firmware/imx/sdma/sdma-imx7d.bin
}
