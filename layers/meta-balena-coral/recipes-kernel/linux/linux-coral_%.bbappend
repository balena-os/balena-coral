inherit kernel-resin

# qcacld driver build fails
# if this config is not set as built-in
RESIN_CONFIGS_append = " \
    cfg80211_builtin \
"

RESIN_CONFIGS[cfg80211_builtin] = " \
    CONFIG_CFG80211=y \
"
