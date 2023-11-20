/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import { Template } from './Template';

/**
 * tag function applicata per eseguire il
 * render dei template sui vari componenti
 *
 * Es.
 *
 * let person = 'Mike';
 * let age = 28;
 * let template = html`That ${ person } is a ${ age }`
 *
 * strings = ['That ', ' is a ']
 * values = ['Mike', 28]
 *
 * @param {*} strings rappresenta la lista delle stringhe del template
 * @param  {...any} values rappresenta la lista dei valori dinamici presenti su template
 */
export async function html(strings, ...values) {
    return await new Template(strings, values).render();
}