package andre.gautier.projfinalandreeduardo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;

import andre.gautier.projfinalandreeduardo.R;
import andre.gautier.projfinalandreeduardo.model.Cliente;


public class ClienteAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Cliente> clientes;

    private static ClickListener clickListener;

    public ClienteAdapter(Context context, ArrayList<Cliente> clientes) {
        this.context = context;
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.linha_cliente,
                parent,
                false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder h = (ViewHolder) holder;

        Cliente c = clientes.get(position);

        h.tvNome.setText("Nome: " + c.getNome());
        h.tvSexo.setText("Sexo: " + c.getSexo());
        h.tvIdade.setText(String.valueOf("Idade: " + c.getIdade()));
        h.tvCPF.setText(String.valueOf("CPF: " + c.getCpf()));
        h.tvCEP.setText(String.valueOf("CEP: " + c.getEndereco().getCep()));
        h.tvRua.setText(("Rua: " + c.getEndereco().getRua()));
        h.tvNumero.setText(String.valueOf("Nº: " + c.getEndereco().getNumero()));
        h.tvBairro.setText(("Bairro: " + c.getEndereco().getBairro()));
        h.tvCidade.setText(("Cidade: " + c.getEndereco().getCidade()));
        h.tvUF.setText(("UF: " + c.getEndereco().getEstado()));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView tvNome;
        private final TextView tvSexo;
        private final TextView tvCPF;
        private final TextView tvIdade;
        private final TextView tvCEP;
        private final TextView tvRua;
        private final TextView tvNumero;
        private final TextView tvBairro;
        private final TextView tvCidade;
        private final TextView tvUF;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvNome = itemView.findViewById(R.id.lc_tv_nome);
            tvSexo = itemView.findViewById(R.id.lc_tv_sexo);
            tvCPF = itemView.findViewById(R.id.lc_tv_cpf);
            tvIdade = itemView.findViewById(R.id.lc_tv_idade);
            tvCEP = itemView.findViewById(R.id.lc_tv_cep);
            tvRua = itemView.findViewById(R.id.lc_tv_rua);
            tvNumero = itemView.findViewById(R.id.lc_tv_numero);
            tvBairro = itemView.findViewById(R.id.lc_tv_bairro);
            tvCidade = itemView.findViewById(R.id.lc_tv_cidade);
            tvUF = itemView.findViewById(R.id.lc_tv_uf);
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
        ClienteAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

}