package br.com.avaliacao.contas.pagar.domain.data.model.wrapper;

import br.com.avaliacao.contas.pagar.domain.data.model.ModelBusiness;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PagedResponse<T> extends ModelBusiness {

  @JsonProperty("content")
  private List<T> objects = new ArrayList<>();

  private Integer number;
  private Integer size;
  private Integer totalPages;
  private Integer numberOfElements;
  private Integer totalElements;
  private Integer nextPage;
  private Integer previousPage;
  private boolean hasPreviousPage;
  private boolean hasNextPage;
  private boolean hasContent;
  private boolean first;
  private boolean last;

  public List<T> getObjects() {
    return objects;
  }

  public void setObjects(List<T> objects) {
    this.objects = objects;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(final Integer number) {
    this.number = number;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(final Integer size) {
    this.size = size;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(final Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Integer getNumberOfElements() {
    return numberOfElements;
  }

  public void setNumberOfElements(final Integer numberOfElements) {
    this.numberOfElements = numberOfElements;
  }

  public Integer getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(final Integer totalElements) {
    this.totalElements = totalElements;
  }

  public Integer getNextPage() {
    return nextPage;
  }

  public void setNextPage(final Integer nextPage) {
    this.nextPage = nextPage;
  }

  public Integer getPreviousPage() {
    return previousPage;
  }

  public void setPreviousPage(final Integer previousPage) {
    this.previousPage = previousPage;
  }

  public boolean isHasPreviousPage() {
    return hasPreviousPage;
  }

  public void setHasPreviousPage(final boolean hasPreviousPage) {
    this.hasPreviousPage = hasPreviousPage;
  }

  public boolean isHasNextPage() {
    return hasNextPage;
  }

  public void setHasNextPage(final boolean hasNextPage) {
    this.hasNextPage = hasNextPage;
  }

  public boolean isHasContent() {
    return hasContent;
  }

  public void setHasContent(final boolean hasContent) {
    this.hasContent = hasContent;
  }

  public boolean isFirst() {
    return first;
  }

  public void setFirst(final boolean first) {
    this.first = first;
  }

  public boolean isLast() {
    return last;
  }

  public void setLast(final boolean last) {
    this.last = last;
  }
}
