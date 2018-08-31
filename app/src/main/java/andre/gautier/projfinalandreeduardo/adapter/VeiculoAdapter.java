package andre.gautier.projfinalandreeduardo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import andre.gautier.projfinalandreeduardo.R;
import andre.gautier.projfinalandreeduardo.model.Veiculo;

public class VeiculoAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Veiculo> veiculos;

    private static ClickListener clickListener;

    public VeiculoAdapter(Context context, ArrayList<Veiculo> veiculos) {
        this.context = context;
        this.veiculos = veiculos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.linha_veiculo,
                parent,
                false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder h = (ViewHolder) holder;

        Veiculo v = veiculos.get(position);

        h.tvNome.setText("Nome: " + v.getNome());
        h.tvMarca.setText("Marca: " + v.getMarca());
        h.tvAno.setText(String.valueOf("Ano: " + v.getAno()));
        h.tvModelo.setText(String.valueOf("Modelo: " + v.getModelo()));
        h.tvCor.setText("Cor: " + v.getCor());
        h.tvCombustivel.setText("Combustível: " + v.getCombustivel());
        h.tvPortas.setText("Portas: " + v.getPortas());
        h.tvValorCusto.setText(String.valueOf("Preço: R$ " + v.getValorCusto()));
        h.tvAr.setText("Ar Cond: " + v.getComplementos().get(0));
        h.tvDirecao.setText("Dir. Hidráulica: " + v.getComplementos().get(1));
        h.tvAirbag.setText("Airbag: " + v.getComplementos().get(2));

    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView tvNome;
        private final TextView tvMarca;
        private final TextView tvAno;
        private final TextView tvModelo;
        private final TextView tvCor;
        private final TextView tvCombustivel;
        private final TextView tvPortas;
        private final TextView tvValorCusto;
        private final TextView tvAr;
        private final TextView tvDirecao;
        private final TextView tvAirbag;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvNome = itemView.findViewById(R.id.lv_tv_nome);
            tvMarca = itemView.findViewById(R.id.lv_tv_marca);
            tvAno = itemView.findViewById(R.id.lv_tv_ano);
            tvModelo = itemView.findViewById(R.id.lv_tv_modelo);
            tvCor = itemView.findViewById(R.id.lv_tv_cor);
            tvCombustivel = itemView.findViewById(R.id.lv_tv_combustivel);
            tvPortas = itemView.findViewById(R.id.lv_tv_portas);
            tvValorCusto = itemView.findViewById(R.id.lv_tv_valor_custo);
            tvAr = itemView.findViewById(R.id.lv_tv_ar);
            tvDirecao = itemView.findViewById(R.id.lv_tv_direcao);
            tvAirbag = itemView.findViewById(R.id.lv_tv_airbag);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return true;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        VeiculoAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}