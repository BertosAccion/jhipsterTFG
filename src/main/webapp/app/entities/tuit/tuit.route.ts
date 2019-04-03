import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tuit } from 'app/shared/model/tuit.model';
import { TuitService } from './tuit.service';
import { TuitComponent } from './tuit.component';
import { TuitDetailComponent } from './tuit-detail.component';
import { TuitUpdateComponent } from './tuit-update.component';
import { TuitDeletePopupComponent } from './tuit-delete-dialog.component';
import { ITuit } from 'app/shared/model/tuit.model';

@Injectable({ providedIn: 'root' })
export class TuitResolve implements Resolve<ITuit> {
    constructor(private service: TuitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Tuit> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Tuit>) => response.ok),
                map((tuit: HttpResponse<Tuit>) => tuit.body)
            );
        }
        return of(new Tuit());
    }
}

export const tuitRoute: Routes = [
    {
        path: 'tuit',
        component: TuitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'tfgTest1App.tuit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tuit/:id/view',
        component: TuitDetailComponent,
        resolve: {
            tuit: TuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'tfgTest1App.tuit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tuit/new',
        component: TuitUpdateComponent,
        resolve: {
            tuit: TuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'tfgTest1App.tuit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tuit/:id/edit',
        component: TuitUpdateComponent,
        resolve: {
            tuit: TuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'tfgTest1App.tuit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tuitPopupRoute: Routes = [
    {
        path: 'tuit/:id/delete',
        component: TuitDeletePopupComponent,
        resolve: {
            tuit: TuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'tfgTest1App.tuit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
