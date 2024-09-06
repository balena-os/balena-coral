# the machine in this repo are still on kernel 4.14.98,
# which is older than 4.15 so let's not use wireless-regdb-static just yet.
CONNECTIVITY_FIRMWARES:remove = "wireless-regdb-static"

# Brings rampatch_00130302.bin, which came before from
# coral firmware-imx package
CONNECTIVITY_FIRMWARES:append = "linux-firmware-qca"

# Temporary removal for HUP until firmware can be added via hostapp-extensions
CONNECTIVITY_FIRMWARES:remove = " \
    linux-firmware-iwlwifi-135-6 \
    linux-firmware-iwlwifi-3160 \
    linux-firmware-iwlwifi-6000-4 \
    linux-firmware-iwlwifi-6000g2a-6 \
    linux-firmware-iwlwifi-6000g2b-6 \
    linux-firmware-iwlwifi-6050-5 \
    linux-firmware-iwlwifi-7260 \
    linux-firmware-iwlwifi-7265 \
    linux-firmware-iwlwifi-7265d \
    linux-firmware-iwlwifi-8000c \
    linux-firmware-iwlwifi-8265 \
    linux-firmware-iwlwifi-9260 \
"
