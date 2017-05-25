import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Groupe } from './groupe.model';
import { GroupePopupService } from './groupe-popup.service';
import { GroupeService } from './groupe.service';

@Component({
    selector: 'jhi-groupe-delete-dialog',
    templateUrl: './groupe-delete-dialog.component.html'
})
export class GroupeDeleteDialogComponent {

    groupe: Groupe;

    constructor(
        private groupeService: GroupeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'groupeListModification',
                content: 'Deleted an groupe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-groupe-delete-popup',
    template: ''
})
export class GroupeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groupePopupService: GroupePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.groupePopupService
                .open(GroupeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
