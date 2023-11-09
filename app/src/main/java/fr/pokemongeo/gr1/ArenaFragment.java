package fr.pokemongeo.gr1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import fr.pokemongeo.gr1.databinding.ArenaFragmentBinding;

public class ArenaFragment extends Fragment {
    private ArenaFragmentBinding binding;
    private String arenaName;

    public ArenaFragment(String arenaName) {
        this.arenaName = arenaName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.arena_fragment, container, false);
        View rootView = binding.getRoot();

        MaterialButton backButtonMaterial = rootView.findViewById(R.id.backButton);
        MaterialButton captureButtonMaterial = rootView.findViewById(R.id.captureButton);

        backButtonMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the previous fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        captureButtonMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the arena is already captured
                if (!isArenaCaptured(arenaName)) {
                    // If not captured, update the capture status in the database
                    updateCaptureStatusInDatabase(arenaName);
                }

                // Return to the previous fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return rootView;
    }

    private boolean isArenaCaptured(String arenaName) {
        // Implement the logic to check if the arena is already captured
        // You can query the database or use any other method based on your implementation
        // Replace the following line with your logic to check if the arena is captured
        boolean isCaptured = Database.getInstance(requireContext()).isArenaCaptured(arenaName);

        return isCaptured;
    }

    private void updateCaptureStatusInDatabase(String arenaName) {
        // Assuming you have access to the arenaName and captured status
        boolean captured = true; // Replace with the actual capture status

        // Update the capture status in the database
        Database.getInstance(requireContext()).updateCaptureStatus(arenaName, captured);
    }
}
