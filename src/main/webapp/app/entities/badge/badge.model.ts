import { Rooms } from '../rooms';
export class Badge {
    constructor(
        public id?: number,
        public numberbadge?: string,
        public matricule?: string,
        public rooms?: Rooms,
    ) {
    }
}
