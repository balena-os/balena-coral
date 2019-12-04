#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>

/* On BalenaOS the root filesystem
 * is read-only. Therefore we
 * save these files to a tmpfs
 * and create a symlink to where the
 * driver expects them to be.
 */
#define SYSFS_MAC_FORMAT         "%02x:%02x:%02x:%02x:%02x:%02x"
#define BINARY_MAC_FORMAT        "%02x%02x%02x%02x%02x%02x"
#define BT_DRIVER_MAC_BINARY     "/tmp/bt_nv.bin"
#define WIFI_DRIVER_MAC_BINARY   "/tmp/wlan_mac.bin"
#define SYSFS_PATH               "/sys/class/net/eth0/address"
#define DVT_MAC                  "f4:f5:e8"
#define BD_NVITEM                0x02
#define BD_RDWR_PROT             0x00
#define BD_NVITEM_SIZE           0x06

/* This check is done as-is, just like in
 * https://coral.googlesource.com/imx-board-wlan/+/refs
 *         /heads/release-chef/etc/runonce.d/10-set-mac-addresses
 */
bool isDVTMac(const std::string &mac)
{
    if (0 == mac.rfind(DVT_MAC, 0))
        return true;

    return false;
}

std::string getCurrentMac()
{
    std::string mac;
    std::ifstream file;
    file.open(SYSFS_PATH, std::ifstream::in);

    if (!file.good())
        return "00:00:00:00:00:00";

    std::getline(file, mac);

    return mac;
}

std::string getNextMac(const std::string &mac)
{
    int num, arr[6];
    char ret[20];
    std::string fmt;

    // sysfs MAC contains ':' whereas the config binary  ones don't
    fmt = (std::string::npos != mac.rfind(":")) ? SYSFS_MAC_FORMAT : BINARY_MAC_FORMAT;
    std::sscanf(mac.c_str(), fmt.c_str(), &arr[0], &arr[1], &arr[2], &arr[3], &arr[4], &arr[5]);

    num = arr[5] | (arr[4] << 8) | (arr[3]  << 16);
    num++;

    /* As per the original script, we only increment
     * the number formed by the last half of the addr
     */ 
    arr[5] = num & 0xFF;
    arr[4] = num >> 8 & 0xFF;
    arr[3] = num >> 16 & 0xFF;

    sprintf(ret, BINARY_MAC_FORMAT, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);

    return ret;
}

bool writeBTMac()
{
    std::ofstream file;
    bool ret = false;
    int i, arr[9] = { 0, 0, 0, 0, 0, 0, BD_RDWR_PROT, BD_NVITEM, BD_NVITEM_SIZE };

    std::string currentEthMAC(getNextMac(getCurrentMac()));
    std::sscanf(currentEthMAC.c_str(), BINARY_MAC_FORMAT, &arr[0], &arr[1], &arr[2], &arr[3], &arr[4], &arr[5]);

    file.open(BT_DRIVER_MAC_BINARY, std::ios::binary);

    // Write the BT MAC address in the format expected by the driver
    if (file.is_open())
    {
        // Header
        file.write((char*) &arr[7], 1);
        file.write((char*) &arr[6], 1);
        file.write((char*) &arr[8], 1);

        /* Resulting address set by the driver
         * will be the the reversed wifi mac,
         * just like on mendel os.
         */
	for (i = 0; i < 6; i++)
            file.write((char*) &arr[i], 1);

        ret = true;
    }

    return ret;
}

bool writeWifiMacs()
{
    std::ofstream file;
    bool ret = false;
    std::string currentEthMAC(getCurrentMac()), newMac1, newMac2;

    newMac1 = getNextMac(currentEthMAC);
    newMac2 = getNextMac(newMac1);
    file.open(WIFI_DRIVER_MAC_BINARY);

    if (file.is_open())
    {
        file << "Intf0MacAddress=" << newMac1 << std::endl << "Intf1MacAddress=" << newMac2 << std::endl << "END" << std::endl << std::flush;

        ret = true;
    }

    return ret;
}

int main()
{
    std::string currentEthMAC(getCurrentMac());
    
    if (isDVTMac(currentEthMAC))
    {
        fprintf(stderr, "Detected DVT MAC. Will not set Wifi/BT MAC addresses!\n");

        return -1;
    }

    if (!writeWifiMacs())
    {
        fprintf(stderr, "Failed to write Wifi MAC addresses!\n");

        return -1;
    }

    if (!writeBTMac())
    {
        fprintf(stderr, "Failed to write BT MAC addresses!\n");

        return -1;
    }


    return 0;
}
