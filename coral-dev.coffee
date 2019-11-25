deviceTypesCommon = require '@resin.io/device-types/common'
{ networkOptions, commonImg, instructions } = deviceTypesCommon

SWITCH_SD = "Set the BOOT_SELECT switch to the SD-CARD position."
SWITCH_EMMC = "Set the BOOT_SELECT switch to the eMMC position."

postProvisioningInstructions = [
	instructions.BOARD_SHUTDOWN
	instructions.REMOVE_INSTALL_MEDIA
	SWITCH_EMMC
	instructions.BOARD_REPOWER
]

module.exports =
	version: 1
	slug: 'coral-dev'
	name: 'Google Coral Dev Board'
	arch: 'aarch64'
	state: 'experimental'

	stateInstructions:
		postProvisioning: postProvisioningInstructions

	instructions: [
		SWITCH_SD
		instructions.ETCHER_SD
		instructions.FLASHER_WARNING
	].concat(postProvisioningInstructions)

	gettingStartedLink:
		windows: 'http://docs.resin.io/coral-dev/nodejs/getting-started/#adding-your-first-device'
		osx: 'http://docs.resin.io/coral-dev/nodejs/getting-started/#adding-your-first-device'
		linux: 'http://docs.resin.io/coral-dev/nodejs/getting-started/#adding-your-first-device'

	yocto:
		machine: 'coral-dev'
		image: 'resin-image-flasher'
		fstype: 'resinos-img'
		version: 'yocto-warrior'
		deployArtifact: 'resin-image-flasher-coral-dev.resinos-img'
		compressed: true

	options: [ networkOptions.group ]

	configuration:
		config:
			partition:
				primary: 1
			path: '/config.json'

	initialization: commonImg.initialization
