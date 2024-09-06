FILESEXTRAPATHS:prepend:coral-dev := "${THISDIR}/files:"

UBOOT_KCONFIG_SUPPORT = "1"

inherit resin-u-boot

# We cannot update to zeus as we don't have support yet
# in meta-balena. We don't rely or need that either,
# cause we can update to a newer u-boot and kernel
# on warrior too.

SRCBRANCH = "release-day"
SRCREV = "8523a101cd4dda7ca62d3f99c4bd1b3718cca953"
SRC_URI = " \
    git://coral.googlesource.com/uboot-imx;protocol=https;branch=${SRCBRANCH} \
"

BUILD_CPPFLAGS += " -static"

SRCBRANCH:asus-tinker-edge-t = "mendel-day-imx_8m"
SRC_URI:asus-tinker-edge-t = "\
    git://github.com/TinkerEdgeT/mendel-uboot-imx;protocol=https;branch=${SRCBRANCH:asus-tinker-edge-t} \
    file://0001-tools-allow-to-override-python.patch \
    file://0002-ext4-cache-extent-blocks-during-file-reads.patch \
    file://0003-u-boot-Run-crc32-checks.patch \
"

SRC_URI:append = " \
    file://u-boot-Integrate-with-BalenaOS-load-kernel-from-root.patch \
    file://0001-Change-fdt_addr-to-fix-haning-on-Starting-kernel.patch \
    file://0004-scripts-Fix-dtc-version-check.patch \
    file://0005-scripts-Makefile.lib-remove-overridden-target-obj-he.patch \
"

UBOOT_CONFIG[sd] = "mx8mq_phanbell_defconfig,sdcard"
DEPENDS:append = " dtc-native"

do_configure:prepend() {
    cp ${S}/include/fdt.h ${S}/include/libfdt.h ${S}/include/libfdt_env.h ${S}/lib/libfdt/
}
