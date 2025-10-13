import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Title } from "../../components/title/title";
import { Header } from "../../components/header/header";
import { LucideAngularModule } from 'lucide-angular';
import { ListTransaction } from '../../components/list-transaction/list-transaction';


export interface TransactionI {
  id: number,
  title: string,
  date: string,
  value: string,
  type: "entry" | "spent",
  typeMethod: "pix" | "boleto" | "credito"
}

@Component({
  selector: 'app-dashboard',
  imports: [Title, Header, LucideAngularModule, ListTransaction],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
  standalone: true
})
export class Dashboard {

}
