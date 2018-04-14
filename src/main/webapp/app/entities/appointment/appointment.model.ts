import { BaseEntity } from './../../shared';

export class Appointment implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public state?: string,
        public detail?: string,
        public reply?: string,
        public caoch?: number,
        public pr?: number,
    ) {
    }
}
