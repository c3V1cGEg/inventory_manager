let console = null;
let scannedSerialTextField = null;

let log = (message) => {
    if (console != null) {
        console.textContent = console.textContent + message + "\n";
    }
}

let writeSerial = (serialNumber) => {
    if (scannedSerialTextField != null) {
        scannedSerialTextField.value = serialNumber;
    }
}

document.addEventListener("DOMContentLoaded", () => {
    console = document.querySelector("#logconsole");
    scannedSerialTextField = document.querySelector("#serialNumber");

    if (!("NDEFReader" in window)) {
        log("Web NFC is not available. Use Chrome on Android.");
    }

    let startReader = async() => {
        try {
            const ndef = new NDEFReader();
            await ndef.scan();
            log("Scan started");

            ndef.addEventListener("readingerror", () => {
                log("Error: Cannot read data from the NFC tag.");
            });

            ndef.addEventListener("reading", ({message, serialNumber}) => {
                log(`> Serial Number: ${serialNumber}`);
                writeSerial(serialNumber);
            });
        } catch (error) {
            log("Error: " + error);
        }
    }

    startReader();

    /*let scanButton = document.querySelector("#scanButton");
    scanButton.addEventListener("click", async () => {
        startReader();
    });//*/

    /*let scanButton = document.querySelector("#scanButton");
    scanButton.addEventListener("click", async () => {
        log("User clicked scan button");

        try {
            const ndef = new NDEFReader();
            await ndef.scan();
            log("> Scan started");

            ndef.addEventListener("readingerror", () => {
                log("Argh! Cannot read data from the NFC tag. Try another one?");
            });

            ndef.addEventListener("reading", ({message, serialNumber}) => {
                log(`> Serial Number: ${serialNumber}`);
                log(`> Records: (${message.records.length})`);

                if(message.records.length > 0) {
                    log(`> Record[0] id: (${message.records[0].id})`);
                    log(`> Record[0] data: (${message.records[0].data})`);
                    log(`> Record[0] recordType: (${message.records[0].recordType})`);
                    log(`> Record[0] toRecords: (${message.records[0].data.toRecords()})`);
                }

            });
        } catch (error) {
            log("Argh! " + error);
        }
    });//*/

    /*let writeButton = document.querySelector("#writeButton");
    writeButton.addEventListener("click", async () => {
        log("User clicked write button");

        try {
            const ndef = new NDEFReader();
            await ndef.write("Hello world!");
            log("> Message written");
        } catch (error) {
            log("Argh! " + error);
        }
    });//*/

    /*let makeReadOnlyButton = document.querySelector("#makeReadOnlyButton");
    makeReadOnlyButton.addEventListener("click", async () => {
        log("User clicked make read-only button");

        try {
            const ndef = new NDEFReader();
            await ndef.makeReadOnly();
            log("> NFC tag has been made permanently read-only");
        } catch (error) {
            log("Argh! " + error);
        }
    });*/
});