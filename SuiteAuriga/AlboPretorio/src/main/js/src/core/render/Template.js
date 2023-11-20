/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Component } from '../Component';

/**
 * Classe permette di eseguire il render dei template
 * dei componenti
 */
export class Template {
    /**
     * Construttore
     * @param {*} strings contiene la lista delle stringhe del template
     * @param {*} values contiene la lista dei valori presenti sul template
     */
    constructor(strings, values) {
        this.strings = strings;
        this.values = values;
    }

    /**
     * Metodo asincrono utilizzato per eseguire il render
     * di ogni parte del template dei componenti
     */
    async render() {
        let str = '';

        for (let i = 0; i < this.strings.length; i++) {
            str += await this._renderChunk(i);
        }
        return str;
    }

    /**
     * Metodo asincrono interno utilizzato per eseguire il render
     * del chunk identificato da index
     * @param {*} index indice del chunk
     */
    async _renderChunk(index) {
        return this.strings[index] + (await this._renderValue(this.values[index]));
    }

    /**
     * Metodo asincrono interno utilizzato per eseguire il render
     * del valore idendificato da value
     * @param {*} value valore di cui eseguire il render
     */
    async _renderValue(value) {
        let str = '';
        // si controlla che sia definito
        if (value) {
            if (this._isArray(value)) {
                // se è un array si esegue il render della lista di value
                str += await this._renderArrayValues(value);
            } else {
                // altrimenti si esegue il render del componente o della stringa
                str = await this._renderComponentOrString(value);
            }
        }
        return str;
    }

    /**
     * Metodo asincrono interno utilizzato per eseguire il render
     * di una lista di valori
     * @param {*} values lista dei valori
     */
    async _renderArrayValues(values) {
        let str = '';
        // scorre tutti i valori
        for (let k = 0; k < values.length; k++) {
            // esegue il render del componente o della stringa
            str += await this._renderComponentOrString(values[k]);
        }
        return str;
    }

    /**
     * Metodo asincrono interno utilizzato per eseguire il render
     * di un componente o di un qualsiasi valore che abbia definito
     * il metodo toString()
     * @param {*} value valore di cui eseguire il render
     */
    async _renderComponentOrString(value) {
        // constrolla se è un componente
        if (this._isComponent(value)) {
            // esegue la before_render del componente
            await value.before_render();
            // esegue l'after_render del componente differita per avere i nodi presenti sul DOM
            setTimeout(async() => {
                await value.after_render();
            }, 0);
            // esegue la render del componente
            return await value.render();
        }

        // richiamato implicitamente il metodo toString() del value
        return value;
    }

    _isArray(value) {
        return Array.isArray(value);
    }

    _isComponent(value) {
        return value instanceof Component;
    }
}