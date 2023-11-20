/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
export class Url {
  static parseRequest() {
    let url = location.hash.slice(1).toLowerCase() || '/';
    let r = url.split('/');
    let request = {
      resource: null,
      id: null,
      verb: null
    };
    if (r.length > 3) {
      request.resource = r[2] ? r[1] + '/' + r[2] : r[1];
      request.id = r[3];
      request.verb = r[4];
    } else {
      request.resource = r[1] && r[1] !== ''? r[1] : 'albo';
      request.id = r[2];
      request.verb = r[3];
    }
    

    return request;
  }

  static getActiveRoute() {
    let url = location.hash.slice(1).toLowerCase() || '/';
    let r = url.split('/');
    return r.length ? url : url[1];
  }
}
