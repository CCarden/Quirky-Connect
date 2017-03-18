 /*
 *  Quirky Nimbus
 *
 *  Author: todd@wackford.net
 *  Date: 2014-02-22
 *
 *****************************************************************
 *     Setup Namespace, capabilities, attributes and commands
 *****************************************************************
 * Namespace:			"wackford"
 *
 * Capabilities:		"polling"
 *						"refresh"
 *
 * Custom Attributes:	"dial"
 *						"info"
 *
 * Custom Commands:		"none"
 *
 *****************************************************************
 *                       Changes
 *****************************************************************
 *
 *  Change 1:	2014-03-10
 *				Documented Header
 *
 *****************************************************************
 *                       Code
 *****************************************************************
 */
 // for the UI
metadata {
	// Automatically generated. Make future change here.
	definition (name: "Quirky Nimbus", namespace: "wackford", author: "todd@wackford.net", oauth: true) {
		capability "Refresh"
		capability "Polling"

		attribute "dial", "string"
		attribute "info", "string"
        
        command "dial"
	}


	tiles {
		standardTile("dial", "device.dial", width: 2, height: 2){
        	state "dial", label: '${currentValue}', icon:"st.custom.quirky.quirky-device"
        }      
        valueTile("info", "device.info", inactiveLabel: false, decoration: "flat") {
			state "info", label:'Dial is displaying ${currentValue}', unit:""
		}
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
			state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
	}
	main(["dial"])
    details(["dial","info","refresh" ])
}

// parse events into attributes
def parse(description) {

	if ( description == "updated" )
    	return
        
	log.debug "parse() - $description"
	def results = []

	if (description?.name && description?.value)
	{
		results << sendEvent(name: "${description?.name}", value: "${description?.value}")
	}
}

def uninstalled() {
	log.debug "Executing 'uninstall' in child"
	try {
		if (parent) {
			parent.uninstallChildDevice(this)
		}
	} catch (e) {
		logger.warn "Exception in Quirky Nimbus uninstall: $e"
	}
}

def poll() {
	log.debug "Nimbus executing 'pollNimbus'"
	parent.pollNimbus(this)
}

def refresh() {
	log.debug "Nimbus executing 'refresh'"
	parent.pollNimbus(this)
}
