import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Rooms } from './rooms.model';
import { RoomsService } from './rooms.service';

@Component({
    selector: 'jhi-rooms-detail',
    templateUrl: './rooms-detail.component.html'
})
export class RoomsDetailComponent implements OnInit, OnDestroy {

    rooms: Rooms;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private roomsService: RoomsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRooms();
    }

    load(id) {
        this.roomsService.find(id).subscribe((rooms) => {
            this.rooms = rooms;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRooms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'roomsListModification',
            (response) => this.load(this.rooms.id)
        );
    }
}
