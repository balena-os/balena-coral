do_deploy_append_coral-dev() {
     sed -i 's/type:veth/&;interface-name:p2p0/g' ${D}${sysconfdir}/NetworkManager/NetworkManager.conf

     cat >> ${D}${sysconfdir}/NetworkManager/NetworkManager.conf <<EOF

[device]
wifi.scan-rand-mac-address=no
EOF

}
