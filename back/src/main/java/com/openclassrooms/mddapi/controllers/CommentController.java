package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les commentaires.
 * <p>
 * Ce contrôleur fournit des endpoints pour ajouter, récupérer, mettre à jour et supprimer des commentaires.
 * </p>
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Ajoute un nouveau commentaire.
     *
     * @param commentDto Le DTO du commentaire contenant les données nécessaires.
     * @return ResponseEntity contenant le commentaire ajouté.
     */
    @PostMapping
    public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(commentDto));
    }
    /**
     * Met à jour un commentaire existant.
     *
     * @param id         L'identifiant du commentaire à mettre à jour.
     * @param commentDto Le DTO contenant les nouvelles données du commentaire.
     * @return ResponseEntity contenant le commentaire mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(id, commentDto));
    }

    /**
     * Supprime un commentaire par son ID.
     *
     * @param id L'identifiant du commentaire à supprimer.
     * @return ResponseEntity contenant un message de succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
