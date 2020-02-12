Change log
-----------

# v2.46.1+rev3
## (2020-02-12)

* Update links to getting started guide from resin.io to balena.io [Gareth Davies]

# v2.46.1+rev2
## (2020-01-17)

* Change the state to 'new' in the coffee file [Vicentiu Galanopulo]

# v2.46.1+rev1
## (2020-01-11)


<details>
<summary> Update meta-balena from v2.45.1 to v2.46.1 [Alexandru Costache] </summary>

> ## meta-balena-2.46.1
> ### (2020-01-01)
> 
> * Disable by default the option to stop u-boot autoboot by pressing CTRL+C in all OS versions [Florin Sarbu]
> * Increase NTP polling time to around 4.5 hours. [Alex Gonzalez]
> * Disable the option to stop u-boot autoboot by pressing CTRL+C in production OS version [Florin Sarbu]

> ## meta-balena-2.46.0
> ### (2019-12-23)
> 
> * Update to ModemManager v1.12.2 [Zahari Petkov]
> * Update libmbim to version 1.20.2 [Zahari Petkov]
> * Update libqmi to version 1.24.2 [Zahari Petkov]
> * Update balena-supervisor to v10.6.27 [Cameron Diver]
> * Tweak how the flasher asserts that internal media is valid for being installed balena OS on [Florin Sarbu]
> * Remove networkmanager stale temporary files at startup [Alex Gonzalez]
> * networkmanager: Rework patches to remove fuzzing [Alex Gonzalez]
> * Update openvpn to v2.4.7 [Will Boyce]
> * Enable kernel configs for USB_SERIAL, USB_SERIAL_PL2303 and HFS for all devices [Zubair Lutfullah Kakakhel]
> * image-resin.bbclass: Mark do_populate_lic_deploy with nostamp [Zubair Lutfullah Kakakhel]
> * Namespace the hello-world healthcheck image [Zubair Lutfullah Kakakhel]
> * Update balena-supervisor to v10.6.17 [Cameron Diver]
> * Update balena-supervisor to v10.6.13 [Cameron Diver]
> * Update CODEOWNERS [Zubair Lutfullah Kakakhel]
</details>

# v2.45.1+rev3
## (2019-12-18)

* coral-dev: Add sd step after switching boot pins [Alexandru Costache]

# v2.45.1+rev2
## (2019-12-04)

* coral-dev: Set blink to unsupported [Alexandru Costache]

# v2.45.1+rev1
## (2019-12-04)

* balena-yocto-scripts: Add submodule at v1.5.2 [Alexandru Costache]
