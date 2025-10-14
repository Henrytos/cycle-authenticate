import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Title } from "../../components/title/title";
import { Header } from "../../components/header/header";
import { LucideAngularModule } from 'lucide-angular';
import { ListTransaction } from '../../components/list-transaction/list-transaction';
// IMPORTAÇÕES CORRETAS PARA O CHART.JS
import {
  Chart,
  ArcElement, // Elemento para o gráfico de pizza
  PieController, // Controlador para o gráfico de pizza
  Tooltip,
  Legend
} from 'chart.js';


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
// Adiciona OnInit à implementação para consistência
export class Dashboard implements OnInit, AfterViewInit {

  spendingByCategory = [
    {
      id: 1,
      type: "Moradia",
      progress: 50,
      value: 2500
    },

    {
      id: 2,
      type: "Alimentação",
      progress: 40,
      value: 1200
    },
    {
      id: 3,
      type: "Saúde",
      progress: 30,
      value: 320
    }
  ];

  @ViewChild('chartCanvas') chartCanvas!: ElementRef;

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {

    // **REGISTRO DOS COMPONENTES NECESSÁRIOS PARA O GRÁFICO DE PIZZA**
    Chart.register(ArcElement, PieController, Tooltip, Legend);

    if (this.chartCanvas) {
      new Chart(this.chartCanvas.nativeElement, {
        type: 'doughnut',
        data: {
          labels: ['Ganhos', 'Gastos', 'Investimentos'],
          datasets: [{
            label: 'Vendas',
            data: [12, 4, 3],
            backgroundColor: [
              'rgba(85, 176, 46,0.8)',
              'rgba(233, 48, 48,0.8)',
              'rgba(255, 255, 255, 0.8)'
            ],
            borderColor: [
              'rgba(85, 176, 46,0.8)',
              'rgba(233, 48, 48,0.8)',
              'rgba(255, 255, 255, 0.8)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      });
    } else {
      console.error("Erro: O Canvas 'chartCanvas' não foi encontrado no template.");
    }
  }
}
