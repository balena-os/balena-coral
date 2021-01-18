FILESEXTRAPATHS_prepend_coral-dev := "${THISDIR}/files:"

UBOOT_KCONFIG_SUPPORT = "1"

inherit resin-u-boot

# We cannot update to zeus as we don't have support yet
# in meta-balena. We don't rely or need that either,
# cause we can update to a newer u-boot and kernel
# on warrior too.

SRCBRANCH = "release-day"
SRCREV = "8523a101cd4dda7ca62d3f99c4bd1b3718cca953"


SRC_URI_append = " \
    file://u-boot-Integrate-with-BalenaOS-load-kernel-from-root.patch \
"
