# the machine in this repo are still on kernel 4.14.98,
# which is older than 4.15 so let's not use wireless-regdb-static just yet.
CONNECTIVITY_FIRMWARES:remove = "wireless-regdb-static"

# Brings rampatch_00130302.bin, which came before from
# coral firmware-imx package
CONNECTIVITY_FIRMWARES:append = "linux-firmware-qca"
