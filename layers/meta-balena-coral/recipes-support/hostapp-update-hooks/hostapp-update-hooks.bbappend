FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

HOSTAPP_HOOKS_append = " \
                   99-resin-uboot \
                   99-coral-dev-imxboot \
"
