DESCRIPTION = "U-boot bootloader mkimage tool"
LICENSE = "GPL"
SECTION = "bootloader"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "openssl"

require recipes-bsp/u-boot/u-boot.inc

#EXTRA_OEMAKE_class-target = 'CROSS_COMPILE="${TARGET_PREFIX}" CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" V=1'
EXTRA_OEMAKE_class-native = 'CC="${BUILD_CC} --sysroot=${STAGING_DIR_NATIVE} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"  V=1'
EXTRA_OEMAKE_class-nativesdk = 'CC="${CC} --sysroot=${STAGING_DIR_NATIVE} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" V=1'

UBOOT_KCONFIG_SUPPORT = "1"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

# We cannot update to zeus as we don't have support yet
# in meta-balena. We don't rely or need that either,
# cause we can update to a newer u-boot and kernel
# on warrior too.

SRCBRANCH = "release-day"
SRCREV = "8523a101cd4dda7ca62d3f99c4bd1b3718cca953"

SRC_URI = "\
    git://coral.googlesource.com/uboot-imx;protocol=https;branch=${SRCBRANCH} \
    file://0001-tools-allow-to-override-python.patch \
    file://0002-ext4-cache-extent-blocks-during-file-reads.patch \
    file://default-gcc.patch \
"

SRCBRANCH_asus-tinker-edge-t = "mendel-day-imx_8m"
SRC_URI_asus-tinker-edge-t = "\
    git://github.com/TinkerEdgeT/mendel-uboot-imx;protocol=https;branch=${SRCBRANCH_asus-tinker-edge-t} \
    file://0001-tools-allow-to-override-python.patch \
    file://0002-ext4-cache-extent-blocks-during-file-reads.patch \
"

S = "${WORKDIR}/git"

LOCALVERSION ?= "-${SRCBRANCH}"

BOOT_TOOLS = "imx-boot-tools"

inherit native

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

do_compile () {
        oe_runmake sandbox_defconfig
	oe_runmake tools
}

do_install () {
	install -d ${D}${bindir}/
	install -m 0755 tools/mkimage ${D}${bindir}/uboot-mkimage
	ln -sf uboot-mkimage ${D}${bindir}/mkimage
}
BBCLASSEXTEND = "native nativesdk"
