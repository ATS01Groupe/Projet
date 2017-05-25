import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RoomsComponent } from './rooms.component';
import { RoomsDetailComponent } from './rooms-detail.component';
import { RoomsPopupComponent } from './rooms-dialog.component';
import { RoomsDeletePopupComponent } from './rooms-delete-dialog.component';

import { Principal } from '../../shared';

export const roomsRoute: Routes = [
    {
        path: 'rooms',
        component: RoomsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.rooms.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rooms/:id',
        component: RoomsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.rooms.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roomsPopupRoute: Routes = [
    {
        path: 'rooms-new',
        component: RoomsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.rooms.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rooms/:id/edit',
        component: RoomsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.rooms.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rooms/:id/delete',
        component: RoomsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.rooms.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
