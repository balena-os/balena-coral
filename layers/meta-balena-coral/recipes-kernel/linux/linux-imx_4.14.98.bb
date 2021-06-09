# Copyright (C) 2020 Mirza Krak

# Part of this recipe is borrowed from zeus branch linux-imx_4.14.%.bbappend available at
# https://github.com/mirzak/meta-coral/tree/zeus/meta-coral-bsp/recipes-kernel/linux
# at commit SHA 448c000cd3e4514a412bf789a209ffbf908c5110
#
# We do so to update from kernel 4.9 to 4.14.98 in our warrior based setup.

FILESEXTRAPATHS_prepend_coral-dev := "${THISDIR}/${PN}:"

# Let's grab the essential and merge upstream linux-imx-4.14.98 recipe
# into this one, to turn it into a complete recipe.
require recipes-kernel/linux/linux-imx.inc
DEPENDS += "lzop-native bc-native"
DEFAULT_PREFERENCE = "1"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

SUMMARY_coral-dev = "Linux Kernel provided and supported by Google for Coral Dev Board"

SRCBRANCH_coral-dev = "release-day"
SRCREV_coral-dev = "f19426310e2e0ff490a3160a64a05018d7f6c536"

SRC_URI_coral-dev = "\
    git://coral.googlesource.com/linux-imx;protocol=https;branch=${SRCBRANCH} \
    file://defconfig \
    file://0001-compiler-attributes-add-support-for-copy-gcc-9.patch \
    file://0002-include-linux-module.h-copy-init-exit-attrs-to-.patch \
"

SUMMARY_asus-tinker-edge-t = "Linux Kernel provided and supported by ASUS for ASUS Tinker Edge T"

SRCBRANCH_asus-tinker-edge-t = "mendel-day-imx_8m"
SRCREV_asus-tinker-edge-t = "1d74a04725699109322a97ee02352507dd2f16c1"

SRC_URI_asus-tinker-edge-t = "\
    git://github.com/TinkerEdgeT/mendel-linux-imx;protocol=https;branch=${SRCBRANCH_asus-tinker-edge-t} \
    file://defconfig \
    file://0001-compiler-attributes-add-support-for-copy-gcc-9.patch \
    file://0002-include-linux-module.h-copy-init-exit-attrs-to-.patch \
    file://0003-Enable-uart1.patch \
"

# As we use the 'defconfig' from Mendel OS (Debian) build scripts, we must also
# replicate the configure step to merge it.
do_configure_prepend_coral-dev() {
    oe_runmake_call -C ${S} CC="${KERNEL_CC}" O=${B} defconfig
    cat "${WORKDIR}/defconfig" | tee -a "${B}/.config"
    oe_runmake_call -C ${S} CC="${KERNEL_CC}" O=${B} olddefconfig
}
