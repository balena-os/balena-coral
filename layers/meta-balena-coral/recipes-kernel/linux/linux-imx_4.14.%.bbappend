inherit kernel-resin

# qcacld driver build fails
# if this config is not set as built-in
BALENA_CONFIGS_append = " \
    cfg80211_builtin \
"

BALENA_CONFIGS[cfg80211_builtin] = " \
    CONFIG_CFG80211=y \
"

# This helps the kernel version
# match the one of the container installed
# galcore module from imx-gpu-viv deb
LOCALVERSION = "-imx"
SCMVERSION="n"
