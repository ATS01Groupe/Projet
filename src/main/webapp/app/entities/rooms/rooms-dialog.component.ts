import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Rooms } from './rooms.model';
import { RoomsPopupService } from './rooms-popup.service';
import { RoomsService } from './rooms.service';
import { Badge, BadgeService } from '../badge';
import { Client, ClientService } from '../client';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rooms-dialog',
    templateUrl: './rooms-dialog.component.html'
})
export class RoomsDialogComponent implements OnInit {

    rooms: Rooms;
    authorities: any[];
    isSaving: boolean;

    badges: Badge[];

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private roomsService: RoomsService,
        private badgeService: BadgeService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.badgeService
            .query({filter: 'rooms(romsnumber)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.rooms.badge || !this.rooms.badge.id) {
                    this.badges = res.json;
                } else {
                    this.badgeService
                        .find(this.rooms.badge.id)
                        .subscribe((subRes: Badge) => {
                            this.badges = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.clientService.query()
            .subscribe((res: ResponseWrapper) => { this.clients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rooms.id !== undefined) {
            this.subscribeToSaveResponse(
                this.roomsService.update(this.rooms));
        } else {
            this.subscribeToSaveResponse(
                this.roomsService.create(this.rooms));
        }
    }

    private subscribeToSaveResponse(result: Observable<Rooms>) {
        result.subscribe((res: Rooms) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Rooms) {
        this.eventManager.broadcast({ name: 'roomsListModification', content: 'OK'});
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

    trackBadgeById(index: number, item: Badge) {
        return item.id;
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rooms-popup',
    template: ''
})
export class RoomsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private roomsPopupService: RoomsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.roomsPopupService
                    .open(RoomsDialogComponent, params['id']);
            } else {
                this.modalRef = this.roomsPopupService
                    .open(RoomsDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
