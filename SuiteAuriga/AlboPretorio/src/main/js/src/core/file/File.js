/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { html } from "../render/html";
import AlboService from "../../services/AlboService";
import './File.css';
import { IconButton } from "../button/IconButton";
import { Link } from "../link/Link";
import { ImprontaPopup } from "./ImprontaPopup";
import $ from 'jquery';


export class File extends Component {

    constructor(file) {
        super();
        file.dimensione = file && file.dimensione ? Math.floor(file.dimensione / 1024) : "N/A";
        this.file = file;
    }

    async render() {
        let verificafirmafun = this.verificaFirma.bind(this);
        let downloadfun = this.download.bind(this);
        let sbustafun = this.sbustaFile.bind(this);
        let previewfun = this.preview.bind(this);
        let timbrafun = this.downloadFileTimbrato.bind(this);
        let improntafun = this.visualizzaImpronta.bind(this);
        if (this.file) {
            let id = this.file.uriFile.substr(this.file.uriFile.lastIndexOf('/') + 1, this.file.uriFile.length);
            if (this.file.flgFirmato && this.file.flgFirmato == '1' && this.file.flgConvertibilePdf && this.file.flgConvertibilePdf == '1') {
                if ((this.file.displayFilename && this.file.displayFilename.indexOf('.p7m') >= 0 && this.file.displayFilename.indexOf('.p7m') == this.file.displayFilename.length - 4)
                    || (this.file.mimetype && this.file.mimetype == "application/x-pkcs7-mime" || this.file.mimetype == "application/pkcs7-mime")) {
                    return await html`
                    ${new Link(this.file.displayFilename, "preview" + id, previewfun, "text-info")}&nbsp; ${this.file.dimensione}&nbspKB &nbsp; 
                    ${new IconButton("Scarica file", "download" + id, downloadfun, "file-download")}
                    ${new IconButton("Verifica firme", "verificaFirma" + id, verificafirmafun, "award")}
                    ${new IconButton("Estrai file contenuto nel p7m", "sbusta" + id, sbustafun, "envelope-open")}
                    ${new IconButton("Scarica versione per stampa", "versioneTimbrata" + id, timbrafun, "print")}
                    ${new IconButton("Visualizza Impronta", "visualizzaImpronta" + id, improntafun, "fingerprint")}
                    ${new ImprontaPopup(this.file.displayFilename, "improntapopup" + id, this.file.impronta, this.file.algoritmoImpronta, this.file.encodingImpronta)}  
                    `;
                } else {
                    return await html`
                    ${new Link(this.file.displayFilename, "preview" + id, previewfun, "text-info")}&nbsp; ${this.file.dimensione}&nbspKB &nbsp; 
                    ${new IconButton("Scarica file", "download" + id, downloadfun, "file-download")}
                    ${new IconButton("Verifica firme", "verificaFirma" + id, verificafirmafun, "award")}
                    ${new IconButton("Scarica versione per stampa", "versioneTimbrata" + id, timbrafun, "print")}
                    ${new IconButton("Visualizza Impronta", "visualizzaImpronta" + id, improntafun, "fingerprint")}
                    ${new ImprontaPopup(this.file.displayFilename, "improntapopup" + id, this.file.impronta, this.file.algoritmoImpronta, this.file.encodingImpronta)}
                    `;
                }
            } else if (this.file.flgFirmato && this.file.flgFirmato == '1' && this.file.flgConvertibilePdf && this.file.flgConvertibilePdf != '1') {
                if ((this.file.displayFilename && this.file.displayFilename.indexOf('.p7m') >= 0 && this.file.displayFilename.indexOf('.p7m') == this.file.displayFilename.length - 4)
                    || (this.file.mimetype && this.file.mimetype == "application/x-pkcs7-mime" || this.file.mimetype == "application/pkcs7-mime")) {
                    return await html`
                    ${new Link(this.file.displayFilename, "preview" + id, previewfun, "text-info")}&nbsp; ${this.file.dimensione}&nbspKB &nbsp; 
                    ${new IconButton("Scarica file", "download" + id, downloadfun, "file-download")}
                    ${new IconButton("Verifica firme", "verificaFirma" + id, verificafirmafun, "award")}
                    ${new IconButton("Estrai file contenuto nel p7m", "sbusta" + id, sbustafun, "envelope-open")}
                    ${new IconButton("Visualizza Impronta", "visualizzaImpronta" + id, improntafun, "fingerprint")}
                    ${new ImprontaPopup(this.file.displayFilename, "improntapopup" + id, this.file.impronta, this.file.algoritmoImpronta, this.file.encodingImpronta)}  
                    `;
                } else {
                    return await html`
                    ${new Link(this.file.displayFilename, "preview" + id, previewfun, "text-info")}&nbsp; ${this.file.dimensione}&nbspKB &nbsp; 
                    ${new IconButton("Scarica file", "download" + id, downloadfun, "file-download")}
                    ${new IconButton("Verifica firme", "verificaFirma" + id, verificafirmafun, "award")}
                    ${new IconButton("Visualizza Impronta", "visualizzaImpronta" + id, improntafun, "fingerprint")}
                    ${new ImprontaPopup(this.file.displayFilename, "improntapopup" + id, this.file.impronta, this.file.algoritmoImpronta, this.file.encodingImpronta)}
                    `;
                }
            } else if (this.file.flgConvertibilePdf && this.file.flgConvertibilePdf == '1') {
                return await html`
                ${new Link(this.file.displayFilename, "preview" + id, previewfun, "text-info")}&nbsp; ${this.file.dimensione}&nbspKB &nbsp; 
                ${new IconButton("Scarica file", "download" + id, downloadfun, "file-download")}
                ${new IconButton("Visualizza Impronta", "visualizzaImpronta" + id, improntafun, "fingerprint")}
                ${new IconButton("Scarica versione per stampa", "versioneTimbrata" + id, timbrafun, "print")}
                ${new ImprontaPopup(this.file.displayFilename, "improntapopup" + id, this.file.impronta, this.file.algoritmoImpronta, this.file.encodingImpronta)}
                `;
            } else {
                return await html`
                ${new Link(this.file.displayFilename, "preview" + id, previewfun, "text-info")}&nbsp; ${this.file.dimensione}&nbspKB &nbsp; 
                ${new IconButton("Scarica file", "download" + id, downloadfun, "file-download")}
                ${new IconButton("Visualizza Impronta", "visualizzaImpronta" + id, improntafun, "fingerprint")}
                ${new ImprontaPopup(this.file.displayFilename, "improntapopup" + id, this.file.impronta, this.file.algoritmoImpronta, this.file.encodingImpronta)}
                `;
            }

        } else {
            return await html`<a class="text-info">Nessun file trovato&nbsp;</a>`
        }
    }




    /**
     * Metodo per la preview del file 
     * 
     */
    preview() {
        let that = this;

        if (this.file && this.file.uriFile) {
            if(this.file.flgConvertibilePdf && this.file.flgConvertibilePdf == '1'){
                let popup = document.getElementById("waitPopup");
                popup.style.display = "block";
                let loader = document.getElementById("loader");
                loader.style.display = "block";
                AlboService.getFile(this.file.uriFile, this.file.mimetype, this.file.flgConvertibilePdf, this.file.displayFilename, this.file.flgFirmato)
                    .then(response => {
                        response.arrayBuffer()
                        .then((arrayBuffer) => {
                            const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/pdf' });

                            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                                // IE or Edge
                                window.navigator.msSaveOrOpenBlob(blob, that.file.displayFilename);
                                popup.style.display = "none";
                                loader.style.display = "none"; 
                            }
                            else {
                                // #toolbar=0 per non far apparire il nome alfanumerico 
                                // del blob nella viewer
                                const blobUrl = URL.createObjectURL(blob);
                                if (navigator.userAgent.indexOf("Chrome") === -1){
                                    
                                    var win = open('', 'name', 'height='+window.height+', width='+ window.width);
                                    let  iframe = document.createElement('iframe');
                                    let title = document.createElement('title');

                                    title.appendChild(document.createTextNode(that.file.displayFilename));

                                    iframe.src = blobUrl;
                                    iframe.width = '100%';
                                    iframe.height = '100%';
                                    iframe.style.border = 'none';

                                    win.document.head.appendChild(title);
                                    win.document.body.appendChild(iframe);
                                    win.document.body.style.margin = 0;
                                        
                                    popup.style.display = "none";
                                    loader.style.display = "none";
                                    
                                } else {
                                let win = window.open(blobUrl + '#toolbar=0', "_blank");
                                if (win) {
                                    
                                    win.addEventListener('load', function () {
                                        popup.style.display = "none";
                                        loader.style.display = "none";    
                                        setTimeout(() => {
                                            
                                            win.document.title = that.file.displayFilename;
                                            }, 1000);
                                        }); 
                                } else {
                                    popup.style.display = "none";
                                    loader.style.display = "none"; 
                                    let errorMessage = "Errore durante l'apertura della finestra di anteprima, verificare che sia consentita l'apertura dei pop-up sul browser";
                                    console.warn(errorMessage);
                                    alert(errorMessage);
                                }
                            }
                            }
                        
                        })
                        .catch((error) => {
                            popup.style.display = "none";
                            loader.style.display = "none";
                            console.error("Errore durante il recupero del file", error);
                            alert(error);
                        });
                    }).catch((error) => {
                        popup.style.display = "none";
                        loader.style.display = "none";
                        console.error("Errore durante il recupero del file", error);
                        alert(error);
                    });
                } else {
                    this.download();
                }
                    
        }

    }
    
    /**
     * Funzione di download scatenata al click del pulsante
     */
    download() {
        let that = this;
        if (this.file && this.file.uriFile) {
            let popup = document.getElementById("waitPopup");
            popup.style.display = "block";
            let loader = document.getElementById("loader");
            loader.style.display = "block";
            AlboService.getFile(this.file.uriFile)
                .then(res => {
                    res.arrayBuffer()
                    .then((arrayBuffer) => {
                        const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/octet-stram' });
                        this.downloadFile(that.file.displayFilename, "", blob);
                        popup.style.display = "none";
                        loader.style.display = "none";
                    }).catch((error) => {
                        popup.style.display = "none";
                        loader.style.display = "none";
                        console.error("Errore durante il recupero del file", error);
                        alert(error);
                    });
                })
                .catch((error) => {
                    popup.style.display = "none";
                    loader.style.display = "none";
                    console.error("Errore durante il recupero del file", error);
                    alert(error);
                });
        }
    }

    downloadFileTimbrato() {
        let that = this;
        if (this.file && this.file.uriFile) {
            let popup = document.getElementById("waitPopup");
            popup.style.display = "block";
            let loader = document.getElementById("loader");
            loader.style.display = "block";
            AlboService.getFileTimbrato(this.file.uriFile, this.file.mimetype, this.file.flgConvertibilePdf, this.file.displayFilename, this.file.flgFirmato, this.file.idUd, this.file.idDoc)
                .then(res => {
                    res.arrayBuffer()
                    .then((arrayBuffer) => {
                        const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/octet-stram' });
                        let filename = "";
                        if (that.file.displayFilename.match("/.p7m/i")) {
                            filename = that.file.displayFilename.replaceAll("/.p7m/i", "");
                        } else if (!that.file.displayFilename.match("/.pdf/i")) {
                            filename = that.file.displayFilename.substring(0, that.file.displayFilename.indexOf("."));
                            filename = filename.concat(".pdf");
                        } else {
                            filename = that.file.displayFilename;
                        }
                         
                        this.downloadFile(filename, "", blob);
                        popup.style.display = "none";
                        loader.style.display = "none";
                    }).catch((error) => {
                        popup.style.display = "none";
                        loader.style.display = "none";
                        console.error("Errore durante il recupero del file", error);
                        alert(error);
                    });
                })
                .catch((error) => {
                    popup.style.display = "none";
                    loader.style.display = "none";
                    console.error("Errore durante il recupero del file", error);
                    alert(error);
                });
        }
    }

    /**
     * Metodo per il dowload del file dato il nome, il mimetype e il file in formato base64
     * 
     * @param {*} name 
     * @param {*} mime 
     * @param {*} base64 
     */
    downloadFile(name, mime, blob) {
        
        const blobUrl = URL.createObjectURL(blob);

        // creo un elemento <a> invisibile per scatenare il download
        let blobAnchor = document.createElement('a');
        blobAnchor.href = blobUrl;
        blobAnchor.download = name;
        blobAnchor.style.display = 'none';
        document.body.appendChild(blobAnchor);
        blobAnchor.click();
        document.body.removeChild(blobAnchor);
    }
    /**
     * Metodo per la visualizzazione del file di verifica della firma
     * 
     */
    verificaFirma() {
        let that = this;
        let uri = this.file.uriFile;
        let nomeFile = this.file.displayFilename;
        let impronta = this.file.impronta;
        let algoritmo = this.file.algoritmoImpronta;
        let encoding = this.file.encodingImpronta;
        let nomeCertificato = "Certificato-" + nomeFile;
        let popup = document.getElementById("waitPopup");
        popup.style.display = "block";
        let loader = document.getElementById("loader");
        loader.style.display = "block";
        AlboService.verificaFirma(uri, nomeFile, impronta, algoritmo, encoding)
            .then(response => {
                response.arrayBuffer()
                    .then((arrayBuffer) => {
                        const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/pdf' });

                    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                                        // IE or Edge
                                        popup.style.display = "none";
                                        loader.style.display = "none";
                                        window.navigator.msSaveOrOpenBlob(blob, nomeCertificato);  
                                        
                                    }
                                    else {
                                        const blobUrl = URL.createObjectURL(blob);
                                        
                                        if (navigator.userAgent.indexOf("Chrome") === -1){
                                
                                            var win = open('', 'name', 'height='+window.height+', width='+ window.width);
                                            let  iframe = document.createElement('iframe');
                                            let title = document.createElement('title');
            
                                            title.appendChild(document.createTextNode(nomeCertificato));
            
                                            iframe.src = blobUrl;
                                            iframe.width = '100%';
                                            iframe.height = '100%';
                                            iframe.style.border = 'none';
            
                                            win.document.head.appendChild(title);
                                            win.document.body.appendChild(iframe);
                                            win.document.body.style.margin = 0;
                                                
                                            popup.style.display = "none";
                                            loader.style.display = "none";
                                            
                                            
                                        } else {
                                        let win = window.open(blobUrl + '#toolbar=0', "_blank");
                                        if (win) {
                                            win.addEventListener('load', function () {
                                                popup.style.display = "none";
                                                loader.style.display = "none";    
                                                setTimeout(() => {
                                                    
                                                    win.document.title = nomeCertificato;
                                                    }, 1000);
                                                }); 
                                        } else {
                                            popup.style.display = "none";
                                            loader.style.display = "none"; 
                                            let errorMessage = "Errore durante l'apertura della finestra di anteprima, verificare che sia consentita l'apertura dei pop-up sul browser";
                                            console.warn(errorMessage);
                                            alert(errorMessage);
                                        }
                                    }
                                    }
                                }).catch((error) => {
                                    popup.style.display = "none";
                                    loader.style.display = "none";
                                    console.error("Errore durante la verifica della firma", error);
                                    alert(error);
                                });
                
            })
            .catch((error) => {
                popup.style.display = "none";
                loader.style.display = "none";
                console.error("Errore durante la verifica della firma", error);
                alert(error);
            });
    }

    /**
     * Metodo per sbustare i file firmati cades
     * 
     */
    sbustaFile() {
        let that = this;
        let uri = this.file.uriFile;
        let nomeFile = this.file.displayFilename;
        let popup = document.getElementById("waitPopup");
        popup.style.display = "block";
        let loader = document.getElementById("loader");
        loader.style.display = "block";
        AlboService.sbusta(uri, nomeFile)
            .then(res => {
               
                let nomeFileSbustato = nomeFile.substr(0, nomeFile.lastIndexOf('.'));
                res.arrayBuffer()
                .then((arrayBuffer) => {
                    const blob = new Blob([new Uint8Array(arrayBuffer)], {type: 'application/octet-stram' });
                    this.downloadFile(nomeFileSbustato, "", blob);
                    popup.style.display = "none";
                    loader.style.display = "none";
                }).catch((error) => {
                    popup.style.display = "none";
                    loader.style.display = "none";
                    console.error("Errore durante il recupero del contenuto del file", error);
                    alert(error)
                });
            })
            .catch((error) => {
                popup.style.display = "none";
                loader.style.display = "none";
                console.error("Errore durante il recupero del contenuto del file", error);
                alert(error)
            });

    }

    downloadFileSbustato(name, mime, base64) {
        // trasformo il file da base64 in blob altrimenti con file troppo grandi non funziona
        const blob = AlboService.b64toBlob(base64, mime);
        const blobUrl = URL.createObjectURL(blob);

        // creo un elemento <a> invisibile per scatenare il download
        let blobAnchor = document.createElement('a');
        blobAnchor.href = blobUrl;
        blobAnchor.download = name;
        blobAnchor.style.display = 'none';
        document.body.appendChild(blobAnchor);
        blobAnchor.click();
        document.body.removeChild(blobAnchor);
    }

    /**
     * Metodo per visualizzazione impronta
     * 
     */
    visualizzaImpronta(){
        let that = this;
        let id = that.file.uriFile.substr(this.file.uriFile.lastIndexOf('/') + 1, that.file.uriFile.length);
        let popup = document.getElementById("improntapopup"+id);
        popup.style.display = "block";
        let span = document.getElementById("close"+"improntapopup"+id);
        span.onclick = function() {
            popup.style.display = "none"; 
        }
        window.onclick = function(event) {
            if (event.target == popup) {
                popup.style.display = "none";
            }
          }
    }
}