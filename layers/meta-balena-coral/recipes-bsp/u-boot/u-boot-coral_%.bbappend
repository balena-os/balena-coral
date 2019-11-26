FILESEXTRAPATHS_prepend_coral-dev := "${THISDIR}/files:"

UBOOT_KCONFIG_SUPPORT = "1"

inherit resin-u-boot

SRC_URI_append = " \
    file://0001-u-boot-Enable-CMD_PART-config.patch \
    file://0001-u-boot-Integrate-with-BalenaOS.patch \
    file://0002-Remove-undefs.patch \
    file://0001-Fix-bootcount-issues-due-to-missing-commands.patch \
"
