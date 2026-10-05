import { provideZoneChangeDetection } from "@angular/core";
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/app.component";
import {appConfig} from "./app/app.config";
import localeDe from '@angular/common/locales/de';
import {registerLocaleData} from "@angular/common";


bootstrapApplication(AppComponent, {...appConfig, providers: [provideZoneChangeDetection(), ...appConfig.providers]}).catch(err => console.error(err));
registerLocaleData(localeDe);
