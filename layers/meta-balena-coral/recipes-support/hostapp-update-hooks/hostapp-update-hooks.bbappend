FILESEXTRAPATHS:append := ":${THISDIR}/${PN}"

HOSTAPP_HOOKS:append = " \
                   99-resin-uboot \
                   99-coral-dev-imxboot \
"
