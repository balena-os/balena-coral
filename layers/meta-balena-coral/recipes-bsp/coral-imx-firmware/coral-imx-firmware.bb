FILESEXTRAPATHS_prepend_coral-dev := "${THISDIR}/patches:"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.QualcommAtheros_ath10k;md5=cb42b686ee5f5cb890275e4321db60a8"

# Bluetooth firmwares were mixed up with the u-boot ones
# for some reason. This is why we need use imx-firmware repo.
SRCBRANCH = "release-chef"
SRC_URI = "\
    git://coral.googlesource.com/imx-firmware;protocol=https;branch=${SRCBRANCH} \
"
# This solves the following issue in yocto:
# File nvm_tlv_3.2.bin: git binary diffs are not supported.
# --full-index --binary were used when patches were generated
# so that they can be applied with "git apply".
PATCHTOOL = "git"

# The nvm_tlv_3.2 that works correctly has been added
# after the firmwares were moved to google debs.
SRC_URI_append_coral-dev = " \
    file://0001-Remove-nvm_tlv_3.2.bin.patch \
    file://0002-Add-new-nvm_tlv_3.2.bin-from-NXP.patch \
"

# Firmwares that work have been moved to google debs,
# and this is why we need to use this specific, older revision.
SRCREV = "37845223a898edcc09773bd238226dbccdc90aa9"

inherit allarch

S = "${WORKDIR}/git"

FILES_${PN} += " /lib/firmware/* "

do_install() {
    install -d ${D}/lib/firmware/qca
    install -m 0644 ${S}/nvm_tlv_3.2.bin ${D}/lib/firmware/nvm_tlv_3.2.bin
    install -m 0644 ${S}/qca/rampatch_00130302.bin ${D}/lib/firmware/qca/rampatch_00130302.bin
    ln -s    qca/rampatch_00130302.bin ${D}/lib/firmware/rampatch_tlv_3.2.tlv

    install -m 0644 ${S}/qca/NOTICE.txt ${D}/lib/firmware/qca/NOTICE.txt
    install -m 0644 ${S}/qca/nvm_00130302.bin ${D}/lib/firmware/qca/nvm_00130302.bin
    install -m 0644 ${S}/qca/nvm_00130302.bin ${D}/lib/firmware/qca/nvm_00130302.bin
}
