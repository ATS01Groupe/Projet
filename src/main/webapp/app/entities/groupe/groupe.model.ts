import { Client } from '../client';
export class Groupe {
    constructor(
        public id?: number,
        public namegroupe?: string,
        public client?: Client,
    ) {
    }
}
