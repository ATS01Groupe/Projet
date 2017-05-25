import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Client } from './client.model';
import { ClientPopupService } from './client-popup.service';
import { ClientService } from './client.service';
import { Rooms, RoomsService } from '../rooms';
import { Groupe, GroupeService } from '../groupe';
import { Duration, DurationService } from '../duration';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-client-dialog',
    templateUrl: './client-dialog.component.html'
})
export class ClientDialogComponent implements OnInit {

    client: Client;
    authorities: any[];
    isSaving: boolean;

    rooms: Rooms[];

    groupes: Groupe[];

    durations: Duration[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientService: ClientService,
        private roomsService: RoomsService,
        private groupeService: GroupeService,
        private durationService: DurationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.roomsService
            .query({filter: 'client(fistname)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.client.room || !this.client.room.id) {
                    this.rooms = res.json;
                } else {
                    this.roomsService
                        .find(this.client.room.id)
                        .subscribe((subRes: Rooms) => {
                            this.rooms = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.groupeService.query()
            .subscribe((res: ResponseWrapper) => { this.groupes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.durationService.query()
            .subscribe((res: ResponseWrapper) => { this.durations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.client.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientService.update(this.client));
        } else {
            this.subscribeToSaveResponse(
                this.clientService.create(this.client));
        }
    }

    private subscribeToSaveResponse(result: Observable<Client>) {
        result.subscribe((res: Client) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Client) {
        this.eventManager.broadcast({ name: 'clientListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackRoomsById(index: number, item: Rooms) {
        return item.id;
    }

    trackGroupeById(index: number, item: Groupe) {
        return item.id;
    }

    trackDurationById(index: number, item: Duration) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-popup',
    template: ''
})
export class ClientPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPopupService: ClientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientPopupService
                    .open(ClientDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientPopupService
                    .open(ClientDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
