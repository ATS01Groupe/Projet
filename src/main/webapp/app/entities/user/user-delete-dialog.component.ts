import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { User } from './user.model';
import { UserPopupService } from './user-popup.service';
import { UserService } from './user.service';

@Component({
    selector: 'jhi-user-delete-dialog',
    templateUrl: './user-delete-dialog.component.html'
})
export class UserDeleteDialogComponent {

    user: User;

    constructor(
        private userService: UserService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userListModification',
                content: 'Deleted an user'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-delete-popup',
    template: ''
})
export class UserDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPopupService: UserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userPopupService
                .open(UserDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
