FILESEXTRAPATHS_prepend_coral-dev := "${THISDIR}/files:"

UBOOT_KCONFIG_SUPPORT = "1"

inherit resin-u-boot

# We cannot update to zeus as we don't have support yet
# in meta-balena. We don't rely or need that either,
# cause we can update to a newer u-boot and kernel
# on warrior too.

SRCBRANCH = "release-day"
SRCREV = "8523a101cd4dda7ca62d3f99c4bd1b3718cca953"

SRCBRANCH_asus-tinker-edge-t = "mendel-day-imx_8m"
SRC_URI_asus-tinker-edge-t = "\
    git://github.com/TinkerEdgeT/mendel-uboot-imx;protocol=https;branch=${SRCBRANCH_asus-tinker-edge-t} \
    file://0001-tools-allow-to-override-python.patch \
    file://0002-ext4-cache-extent-blocks-during-file-reads.patch \
"

SRC_URI_append = " \
    file://u-boot-Integrate-with-BalenaOS-load-kernel-from-root.patch \
"
