SUMMARY = "Kernel loadable module for Wifi"
DESCRIPTION = "Build the WLAN kernel module Out of Tree "

# Please consult individual licenses
# in the driver sources
LICENSE = "CLOSED"

SRCBRANCH = "release-day"
LOCALVERSION = "-${SRCBRANCH}"
KERNEL_SRC ?= "git://coral.googlesource.com/linux-imx;protocol=https"

SRC_URI = "git://coral.googlesource.com/imx-board-wlan-src;protocol=https"
SRCREV = "276713bbe1ec417fabbe91baeedad75bcb3bee31"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE += "CONFIG_QCA_CLD_WLAN=m"

# Will also blacklist this module from the wifi pacakge,
# and load it from our own service file, so that our custom
# tool gets time to generate a MAC address. This
# way we avoid using the default fallback MAC
# for multiple devices, as the driver no longer
# waits for the MAC path to be provided before
# initialization in 4.14.98.
KERNEL_MODULE_AUTOLOAD:remove = "wlan"
