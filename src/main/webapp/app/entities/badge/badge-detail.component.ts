import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Badge } from './badge.model';
import { BadgeService } from './badge.service';

@Component({
    selector: 'jhi-badge-detail',
    templateUrl: './badge-detail.component.html'
})
export class BadgeDetailComponent implements OnInit, OnDestroy {

    badge: Badge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private badgeService: BadgeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBadges();
    }

    load(id) {
        this.badgeService.find(id).subscribe((badge) => {
            this.badge = badge;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBadges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'badgeListModification',
            (response) => this.load(this.badge.id)
        );
    }
}
