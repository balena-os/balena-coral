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
	name: 'Coral Dev Board'
	arch: 'aarch64'
	state: 'new'

	stateInstructions:
		postProvisioning: postProvisioningInstructions

	instructions: [
		SWITCH_SD
		instructions.ETCHER_SD
		instructions.EJECT_SD
		instructions.FLASHER_WARNING
	].concat(postProvisioningInstructions)

	gettingStartedLink:
		windows: 'https://www.balena.io/docs/learn/getting-started/coral-dev/python/'
		osx: 'https://www.balena.io/docs/learn/getting-started/coral-dev/python/'
		linux: 'https://www.balena.io/docs/learn/getting-started/coral-dev/python/'

	supportsBlink: false

	yocto:
		machine: 'coral-dev'
		image: 'balena-image-flasher'
		fstype: 'balenaos-img'
		version: 'yocto-warrior'
		deployArtifact: 'balena-image-flasher-coral-dev.balenaos-img'
		compressed: true

	options: [ networkOptions.group ]

	configuration:
		config:
			partition:
				primary: 1
			path: '/config.json'

	initialization: commonImg.initialization
