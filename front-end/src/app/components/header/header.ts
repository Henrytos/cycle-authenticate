import { Component } from '@angular/core';
import { Logo } from "../logo/logo";
import { ActivatedRoute, Router } from '@angular/router';
import { convertToObject } from 'typescript';

@Component({
  selector: 'app-header',
  imports: [Logo],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {

  path: string = "";

  constructor(
    private route: ActivatedRoute
  ) {
    route.data.subscribe(data => {
      console.log(data)
    });
  }
}
