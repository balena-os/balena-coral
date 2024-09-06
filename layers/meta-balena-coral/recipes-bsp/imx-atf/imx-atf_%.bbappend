PV = "2.0+git${SRCPV}"

# Switch from meta-freescale imx-atf on warrior
# to the one used by google in release-day, to
# solve deep suspend/resume issue.
SRCBRANCH = "release-day"
SRC_URI = "git://coral.googlesource.com/imx-atf.git;protocol=https;branch=${SRCBRANCH} \
"

SRCBRANCH:asus-tinker-edge-t = "mendel-day-imx_8m"
SRC_URI:asus-tinker-edge-t = "\
    git://github.com/TinkerEdgeT/mendel-imx-atf;protocol=https;branch=${SRCBRANCH:asus-tinker-edge-t} \
"

SRCREV = "d543fbbb7d72eda0c5aed97d7a50d78f6aefb09f"
