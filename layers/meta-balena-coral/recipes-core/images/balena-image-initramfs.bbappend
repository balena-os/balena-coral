# There is no sufficient space in the rootfs and probably we won't need to migrate on these device types
# also we don't need the recovery module since we have a separate debug uart
PACKAGE_INSTALL_remove = " initramfs-module-recovery
PACKAGE_INSTALL_remove = "initramfs-module-migrate"
