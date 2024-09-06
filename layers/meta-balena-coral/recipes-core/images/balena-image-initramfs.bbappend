# Probably we won't need to migrate on these device types
# also we don't need the recovery module since we have a separate debug uart
# removing these 2 modules saves about 4 MB in the initramfs
PACKAGE_INSTALL:remove = "initramfs-module-recovery"
PACKAGE_INSTALL:remove = "initramfs-module-migrate"
