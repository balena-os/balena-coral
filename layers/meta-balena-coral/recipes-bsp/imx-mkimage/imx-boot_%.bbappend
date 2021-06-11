FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_compile[depends] += "virtual/bootloader:do_deploy"

SRC_URI_append = " file://0001-test.patch"

compile_mx8m() {
    bbnote 8MQ/8MM boot binary build
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        bbnote "Copy ddr_firmware: ${ddr_firmware} from ${DEPLOY_DIR_IMAGE} -> ${BOOT_STAGING} "
        cp ${DEPLOY_DIR_IMAGE}/${ddr_firmware}               ${BOOT_STAGING}
    done

     cp ${DEPLOY_DIR_IMAGE}/signed_*_imx8m.bin                ${BOOT_STAGING}
     cp ${DEPLOY_DIR_IMAGE}/u-boot-spl.bin-${MACHINE}-${UBOOT_CONFIG} ${BOOT_STAGING}/u-boot-spl.bin
     cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/${UBOOT_DTB_NAME}   ${BOOT_STAGING}
     cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/u-boot-nodtb.bin    ${BOOT_STAGING}/u-boot-nodtb.bin
     cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/mkimage_uboot       ${BOOT_STAGING}
     bbnote "Copied mkimage_uboot from ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/ to ${BOOT_STAGING} "
     echo "<<<<"
     file ${BOOT_STAGING}/mkimage_uboot
     echo "<<<>>>"
     ls -l ${BOOT_STAGING}
     cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/${ATF_MACHINE_NAME} ${BOOT_STAGING}/bl31.bin
     cp ${DEPLOY_DIR_IMAGE}/${UBOOT_NAME}                     ${BOOT_STAGING}/u-boot.bin
}

do_compile_coral-dev() {
    echo ">>>> SOC_FAMILY: ${SOC_FAMILY}, SOC_TARGET: ${SOC_TARGET}, target: ${target}"
    bbnote ">>>> SOC_FAMILY: ${SOC_FAMILY}, SOC_TARGET: ${SOC_TARGET}, target: ${target}"
    pwd
    if [ -f ${BOOT_STAGING}/mkimage_uboot ]; then
        bbnote "Found mkimage_uboot in ${BOOT_STAGING}"
    else
        bbnote "mkimage_uboot is not available in ${BOOT_STAGING}"
    fi;

    compile_${SOC_FAMILY}

    pwd
    if [ -f ${BOOT_STAGING}/mkimage_uboot ]; then
        bbnote "After compile_mx8m: Found mkimage_uboot in ${BOOT_STAGING}"
    else
        bbnote "After compile_mx8m: mkimage_uboot is not available in ${BOOT_STAGING}"
    fi;
    # mkimage for i.MX8
    for target in ${IMXBOOT_TARGETS}; do
        bbnote "building ${SOC_TARGET} - ${target} - ${BOARD}"
	bbnote "Logging improved >>>"
	pwd
        ls .
        make SOC=${SOC_TARGET} ${target} BOARD=${BOARD}
        if [ -e "${BOOT_STAGING}/flash.bin" ]; then
            cp ${BOOT_STAGING}/flash.bin ${S}/${BOOT_CONFIG_MACHINE}-${target}
        fi
    done
}

DEPENDS_append_mx8m = " \
    virtual/bootloader \
"

do_compile[nostamp] = "1"
