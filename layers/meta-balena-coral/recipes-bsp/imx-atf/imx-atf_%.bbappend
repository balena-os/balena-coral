PV = "2.0+git${SRCPV}"

# Switch from meta-freescale imx-atf on warrior
# to the one used by google in release-day, to
# solve deep suspend/resume issue.
SRCBRANCH = "release-day"
SRC_URI = "git://coral.googlesource.com/imx-atf.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-Allow-BUILD_STRING-to-be-set-in-.revision-file.patch \
"
SRCREV = "d543fbbb7d72eda0c5aed97d7a50d78f6aefb09f"
