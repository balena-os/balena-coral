FILESEXTRAPATHS_prepend := "${THISDIR}/patches:"

# Bluez tool hciattach needs to be patched to be able
# to load the firmware for this specific BT chip
SRC_URI_append_coral-dev = " \
    file://0001-bluetooth-Add-bluetooth-support-for-QCA6174-chip.patch \
    file://0002-hciattach-set-flag-to-enable-HCI-reset-on-init.patch \
    file://0003-hciattach-instead-of-strlcpy-with-strncpy-to-avoid-r.patch \
    file://0004-Add-support-for-Tufello-1.1-SOC.patch \
    file://0005-bluetooth-Add-support-for-multi-baud-rate.patch \
"	
