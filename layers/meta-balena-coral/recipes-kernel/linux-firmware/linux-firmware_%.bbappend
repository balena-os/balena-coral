FILES:${PN}-qca += " /lib/firmware/rampatch_tlv_3.2.tlv"

do_install:append(){
    ln -s /lib/firmware/qca/rampatch_00130302.bin ${D}/lib/firmware/rampatch_tlv_3.2.tlv
}
