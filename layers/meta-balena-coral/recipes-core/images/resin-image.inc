IMAGE_FSTYPES_append_coral-dev = " resinos-img"

# zImage only available for up to imx7
# and this kernel is around 30MB
RESIN_BOOT_SIZE = "80960"

RESIN_BOOT_PARTITION_FILES = " \
    ${KERNEL_IMAGETYPE}${KERNEL_INITRAMFS}-${MACHINE}.bin:/Image \
    fsl-imx8mq-phanbell.dtb:/ \
    imx-boot:/ \
"

# Flash signed bootloader to raw image
IMAGE_CMD_resinos-img_append () {
    dd if=${DEPLOY_DIR_IMAGE}/imx-boot-${MACHINE}-sd.bin-flash_evk of=${RESIN_RAW_IMG} conv=notrunc seek=33 bs=1K
}

# These packages are installed for
# both flasher and normal image
IMAGE_INSTALL_append = " \
    imx-board-wlan \
    coral-imx-firmware \
    kernel-module-wlan \
    kernel-devicetree \
"

DEPENDS += " \
    imx-boot \
"
