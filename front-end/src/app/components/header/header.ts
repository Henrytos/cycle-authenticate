import { Component, Input, OnInit, signal } from '@angular/core';
import { Logo } from "../logo/logo";
import { Router, RouterLink } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { GetProfileUserService } from '../../services/get-profile-user-service';
import { LucideAngularModule } from "lucide-angular";

@Component({
  selector: 'app-header',
  imports: [Logo, MatMenuModule, LucideAngularModule, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header implements OnInit {

  @Input() path: "transactions" | "dashboard" | "" = "";
  profileName = signal<string>("")

  constructor(
    private getProfileUserService: GetProfileUserService,
    private router: Router
  ) {

  }
  ngOnInit(): void {
    this.getProfileUserService.execute().subscribe((res) => {
      this.profileName.update(() => res.username)
    })
  }

  logoutUser() {
    localStorage.clear()
    this.router.navigate(["/login"])
  }
}
